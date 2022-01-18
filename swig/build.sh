javaOutputDir=../java/src/main/java/org/kr/jctp
cppOutputDir=../cpp/src
rm -rf $javaOutputDir
mkdir -p $javaOutputDir
rm ${cppOutputDir}/jctp.cpp
rm ${cppOutputDir}/jctp.h
swig -c++ -java -package org.kr.jctp -outdir $javaOutputDir -o $cppOutputDir/jctp.cpp jctp.i
