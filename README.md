#OOPRyhmatoo
#
#NB! Programmiga kompileerimise toimimiseks peab java olema süsteemi Path muutujatesse lisatud.
#Selle jaoks võiksid abi leida siit: http://stackoverflow.com/questions/1672281/environment-variables-for-java-installation
#Kui probleeme tekib, siis minul aitas, kui sisestasin java kodu nii: PATH      : %JAVA_HOME%\bin;your-unique-entries
#(Java kodu lisasin PATH muutuja algusesse)
#
#Programmi eesmärk on etteantud kaustas asuvatelt java failidelt eemaldada package'id.
#
#Programmi testimiseks on vaja enne käivitamist talle ette anda vähemalt üks argument, milleks
#on kausta asukoht, kus olevaid faile hakkatakse läbi vaatama.
#
#Lisaks võib programmile ette anda argumendid -v ja -compile, esimene argument prindib programmi
#töö ajal välja mingisugust lisainfot, teine argument ütleb, et lisaks package'te eemaldamisele 
#soovid sa failid ka kompileerida.
#
#Argumentide lisamine Eclipse'is: http://stackoverflow.com/questions/19646719/eclipse-command-line-arguments
#
#Argumentide lisamine IntelliJ-s: http://stackoverflow.com/questions/2066307/how-do-you-input-commandline-argument-in-intellij-idea
#
#Programmi käivitamiseks käsurealt, tuleb navigeerida kausta, kus asub programmi .class fail(hetkel 
#nimeks unWrapper.class), seejärel käivitada käsuga "java programmi_nimi argument1 arguent2 teekond".
