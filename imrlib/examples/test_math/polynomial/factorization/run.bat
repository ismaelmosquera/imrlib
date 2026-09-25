@echo off
cls
cd bin
java -ea -cp .;..\..\..\..\..\lib\imr-lib.jar TestPolynomialFactorization > out.txt
echo Output saved to bin\out.txt
