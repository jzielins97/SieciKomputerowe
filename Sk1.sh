#!/bin/bash

dir=${HOME}/.wwwUpdater
adresy=(http://www.fizyka.pw.edu.pl/index.php/pl/ http://www.if.pw.edu.pl/~mwas/index.html)
if [ ! -e ${dir} ]
then
  mkdir $dir
fi

for i in "${adresy[@]}"
do
  name=$(echo ${i} | sha1sum | cut -d " " -f 1)
  if [ ! -e ${dir}/${name} ]
    then
      wget -q $i -O ${dir}/${name}
    else
      wget -q $i -O ${dir}/${name}_new
      diff -q ${dir}/${name} ${dir}/${name}_new 1>/dev/null
      if [[ $? == "1" ]]
      then
        echo $i
        mv ${dir}/${name}_new ${dir}/${name}
      else
        rm ${dir}/${name}_new
      fi
    fi
done
