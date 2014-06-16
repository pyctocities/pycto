====== CONFIGURACIÓ SQL ======

1. Instal·lar un SQL al ordinador (xampp, MSsql,...)

2. Descarregar projecte a Eclipse

3. Canviar l'usuari i password del fitxer "hibernat.cfg.xml" del la carpeta "sources" per el vostre.

4. Crear la base de dades "pyctodb": create database pyctodb;

5. "Run As" del projecte


====== CONFIGURACIÓN HTTPS para ECLIPSE ======

Per a poder habilitar HTTPS al servidor tomcat de l'Eclipse hem de fer el següent:

1. Hem d'anar a INICIO i escribiu cmd, apreteu enter i s'obrirà una consola. 

2. Anem al directori C:\Program Files\Java.

3. Aquí depen de la versió que tingui cadascu, entrarem a la carpeta C:\Program Files\Java\jdk1.7.0_60\bin\.

4. Un cop aquí executem:
    keytool -genkey -alias pycto -keyalg RSA -keystore pycto.keystore

5. Ens fara unes preguntes (contestem a tot pycto i la contrasenya la que volguem, després l'haurem de posar a un fitxer de configuració).

6. Copiem el fitxer pycto.keystore (que s'haura creat a la carpeta bin del java) i la copiem al eclipse, a l'apartat Servers, despleguem el servidor que utilitzem (TOMCAT v7.0 Server at localhost-config) i l'enganxem alla.

7. Ara editem el fitxer server.xml de la carpeta TOMCAT v7.0 Server at localhost-config del eclipse.

8. Afegim la següent part de codi (els camps que haurem de canviar es keyStoreFile per el nom del fitxer que hem donat i KeystorePass per la password que haguem posat anteriorment):

<Connector
SSLEnabled="true"
clientAuth="false"
keyAlias="pycto"
keystoreFile="conf/pycto.keystore"
keystorePass="telematica"
maxThreads="200"
port="8081"
scheme="https"
secure="true"
sslProtocol="TLS"
/>

9. Un cop afegit lo anterior, fem run as del rpojecte i entrem per la següent URL:

https://localhost:8081/pycto


10. Ens demanara que aceptem el certificat (la pantalla groga de chrome).

11. El HTTPS surt en vermell i taxat degut aque no ha estat firmat per una CA de confiansa (perque surtis verd hauriem de pagar alguna entitat certificadora perque ens el firmes ells i el chrome el veies de confiansa). 



