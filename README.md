  # OOP FiNAL PROJECT
  This is a project developed by Me ( Lê Mạnh Thắng) and my colleague ( Nguyễn Hữu Thành Danh) for the final oop class;
  This project is an English Learning application with many functions with 2 type of user is Teacher and Student;
  You can experience this by following my instruction:
# Fist, you need to clone this repository on Intellij or similar idle to get the code;
# Second, you have to download and run the database as intructions below:
- Go to this link: https://drive.google.com/drive/folders/1S-hVtZvi4BAqzgE1BShzuCbkzT8HcCtS?usp=sharing
- Down load the folder and then extract 2 file inside it;
- If you haven't install docker, you need to install it first by the tutorials on youtube such as:
  +  https://www.youtube.com/watch?v=ZyBBv1JmnWQ for windows
  +  https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-20-04 for ubuntu
  +  etc.
    # Run these following code to run the database:
      + cd //to the directory contain the files
      + docker import sqlserver_data.tar sqlserver_data
      + docker load -i sql_server.tar
      + docker images // to get the image's name of the sql server's image you have load above
      + docker run -d -p 1433:1433 --name container's_name imgage's_name
        for example: docker run -d -p 1433:1433 --name sqlserver_data sql_server //for the container and imgage you have loaded above
- Go to the IDE and get to the class: src/main/java/Res/SQLQueries and get to the constructor, find the line : "this.c = DriverManager.getConnection(connectionS);"
    #
      + change the variable inside the function to the string: "jdbc:sqlserver://your_docker_container's_ip:1433;Database=data;User=sa;Password=Thanglm#2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;"
      + for example of mine: "jdbc:sqlserver://172.17.0.1:1433;Database=data;User=sa;Password=Thanglm#2006;encrypt=true;trustServerCertificate=true;loginTimeout=30;"
    # if you don't know you container's ip, you can find it by following step:
          open the terminal or cmd
          for windows: ipconfig
          for ubuntu: ip addr
          you can find the ip at docker0: ... inet xxx.xx.x.xx
# Final:
  -run the main class: src/main/java/Gui/EnglishListeningApp.java
  # if you wanna package the project into a real app:
      + get to the pom.xml file and change this line:    <outputFile>place the path of the file you want it to appear </outputFile>
                                         for example:  <outputFile>/home/thanglm2006/OOPFINALPROJECT-1.0-SNAPSHOT.jar</outputFile>
      + install maven by some tutorials on the internet
      + open terminal, change directory to the path of the project; run this block of code: mvn compile
                                                                                            mvn package
    Then open the jar file have just been created and enjoy!
                                                                                      
