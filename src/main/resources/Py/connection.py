
import os
import requests
from gtts import gTTS
import pyodbc
import nltk
from nltk.corpus import words
from nltk import pos_tag, word_tokenize
nltk.download('averaged_perceptron_tagger_eng')
nltk.download('words')
nltk.download('punkt')


try:
    conn = pyodbc.connect(
        'DRIVER={ODBC Driver 17 for SQL Server};'
        'SERVER=172.17.0.1,1433;'
        'DATABASE=data;'
        'UID=sa;'
        'PWD=Thanglm#2006'
    )
    print("Connection successful")
except pyodbc.Error as e:
    print("Error: ", e)

cursor = conn.cursor()

# Function to get Vietnamese meaning using MyMemory API
def get_meaning(word):
    url = f"https://api.mymemory.translated.net/get?q={word}&langpair=en|vi"
    params = {
        'email': 'thanglm.24ai@vku.udn.vn'  # Add your email here as a parameter
    }
    response = requests.get(url, params=params)
    if response.status_code == 200:
        translation = response.json().get('responseData', {}).get('translatedText', '')
        return translation
    else:
        return "Meaning not found"
def map_pos_to_category(tag):
    pos_map = {
        'NN': 'Noun', 'NNS': 'Noun', 'NNP': 'Noun', 'NNPS': 'Noun',
        'VB': 'Verb', 'VBD': 'Verb', 'VBG': 'Verb', 'VBN': 'Verb',
        'VBP': 'Verb', 'VBZ': 'Verb',
        'JJ': 'Adjective', 'JJR': 'Adjective', 'JJS': 'Adjective',
        'RB': 'Adverb', 'RBR': 'Adverb', 'RBS': 'Adverb',
        'PRP': 'Pronoun', 'PRP$': 'Pronoun', 'WP': 'Pronoun', 'WP$': 'Pronoun',
        'DT': 'Determiner', 'IN': 'Preposition', 'CC': 'Conjunction',
        'UH': 'Interjection'
    }
    return pos_map.get(tag, 'Other')  # Default to 'Other' if tag is not in the map
def get_category_name(word):
    pos_tags = pos_tag([word])  # Tag the word
    tag = pos_tags[0][1]  # Get the POS tag of the word
    return map_pos_to_category(tag)

# Function to get IPA pronunciation (simple approximation)


import nltk
from nltk.corpus import cmudict

# Download NLTK data if necessary
nltk.download('cmudict')

# ARPABET to IPA dictionary
ipa_dict = {
    "AA": "ɑ", "AE": "æ", "AH": "ʌ", "AO": "ɔ", "AW": "aʊ", "AY": "aɪ", "EH": "ɛ", "ER": "ɝ",
    "EY": "eɪ", "IH": "ɪ", "IY": "i", "OW": "oʊ", "OY": "ɔɪ", "UH": "ʊ", "UW": "u", "B": "b",
    "CH": "tʃ", "D": "d", "DH": "ð", "F": "f", "G": "g", "HH": "h", "JH": "dʒ", "K": "k", "L": "l",
    "M": "m", "N": "n", "NG": "ŋ", "P": "p", "R": "ɹ", "S": "s", "SH": "ʃ", "T": "t", "TH": "θ",
    "V": "v", "W": "w", "Y": "j", "Z": "z", "ZH": "ʒ"
}

# Load the CMU Pronouncing Dictionary
d = cmudict.dict()

def arpabet_to_ipa(arpabet):
    # Convert a list of ARPABET symbols to IPA, ignoring stress markers
    pronunciation = []
    for symbol in arpabet:
        # Remove the stress marker (0, 1, or 2) from the symbol
        base_symbol = symbol[:-1] if symbol[-1].isdigit() else symbol
        ipa_symbol = ipa_dict.get(base_symbol.upper(), symbol)  # Default to the symbol if no IPA mapping is found
        pronunciation.append(ipa_symbol)
    return "".join(pronunciation)

def get_ipa_pronunciation(word):
    # Retrieve the ARPABET transcription from CMU dictionary
    word = word.lower()
    if word in d:
        # If there are multiple pronunciations, choose the first one
        arpabet = d[word][0]  # Get the first pronunciation
        return "/" + arpabet_to_ipa(arpabet) + "/"
    else:
        return "No"

def load_words_from_file(file_path):
    """Load words from a file and return them as a set."""
    with open(file_path, 'r') as file:
        # Read each line, strip newlines, and convert to lowercase
        words_from_file = set(word.strip().lower() for word in file.readlines())
    return words_from_file


def get_intersection(file_path):
    # Load words from the file
    file_words = load_words_from_file(file_path)

    # Load NLTK words corpus (set of lowercase words)
    nltk_words = set(word.lower() for word in words.words())

    # Find intersection
    intersection = file_words & nltk_words  # Set intersection

    return intersection


# Example usage
file_path = '/home/thanglm2006/Downloads/20k.txt'  # Replace with your file path
intersection = get_intersection(file_path)

common_words = list(intersection)
folder='/home/thanglm2006/Audios'
with open("idx.txt",'r') as f:
    idx = int(f.read())
for word in common_words[idx:idx+10000]:  # Limit for testing
    idx+=1
    meaning = get_meaning(word)
    pronunciation = get_ipa_pronunciation(word)
    category_name = get_category_name(word)
    if meaning== "Meaning not found" or pronunciation== "No":
        print("out of turn")
        conn.commit()
        with open("idx.txt",'w') as f:
            f.write(str(idx+1))
        continue
    if pronunciation== "No":
        continue
    print(word," ",pronunciation," ",meaning," ",category_name)
    # Insert into Vocabulary table
    try:
        cursor.execute("""
            INSERT INTO Vocabulary (Word, Pronunciation, Meaning, CategoryName)
            OUTPUT INSERTED.WordID
            VALUES (?, ?, ?, ?)
        """, word, pronunciation, meaning, category_name)
        word_id = cursor.fetchone()[0]

        # Generate audio file using gTTS
        local_audio_path = f"{word}.mp3"
    except Exception as e:
        print("existed")
        continue

    try:
        tts = gTTS(word, lang='en')  # Generate speech for the word
        lp= os.path.join(folder, local_audio_path)
        tts.save(lp)  # Save locally

        cursor.execute("""
            INSERT INTO AudioForVocab (WordID, FilePath)
            VALUES (?, ?)
        """, word_id, local_audio_path)
    except Exception as e:
        print(f"Failed to process {word}: {e}")
    conn.commit()

# Commit changes to the database
conn.commit()
cursor.close()
conn.close()
with open("idx.txt", 'w') as f:
    f.write(str(idx + 1))
