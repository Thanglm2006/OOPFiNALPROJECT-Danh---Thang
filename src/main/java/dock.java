//docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=Thanglm#2006" `
//        -p 1433:1433 --name sqlserver `
//        -v "C:\DockerDesktopWSL\SQLServer:/var/opt/mssql/data" `
//        -d mcr.microsoft.com/mssql/server:2019-latest
//icacls "C:\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA" /grant "BUILTIN\Users:(F)" /T
//Copy-Item "C:\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\data_log.ldf" "C:\DockerDesktopWSL\SQLServer"
//

//run a container with data backups
//no volume
//docker run --network host --name sqlserver_data -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=Thanglm#2006' -v ~/backups:/var/opt/mssql/data -d mcr.microsoft.com/mssql/server
//
 // volume docker volume create sqlserver_data
//docker run --rm -v sqlserver_data:/var/opt/mssql/data -v /var/backups/data:/backups busybox cp -r /backups/* /var/opt/mssql/data
//docker run --network host --name sqlserver_data -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=Thanglm#2006' -v sqlserver_data:/var/opt/mssql -d mcr.microsoft.com/mssql/server


