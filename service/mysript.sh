#!/bin/sh
#mvn versions:set -DnewVersion=2.0.1-beta1 -DgenerateBackupPoms=false
git checkout master
echo $?
if [[$? == 0 ]]
then
    read -p "Enter your commit message: " commitMessage
    read -p "Are you sure: $commitmessage is your message? [Y/N]" response
    if [ "$response" == "Y" ];
    then
        echo Response yes
    else
        echo Response none
    fi
else
 echo Aborted
fi
