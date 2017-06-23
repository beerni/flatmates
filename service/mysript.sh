#!/bin/sh
#mvn versions:set -DnewVersion=2.0.1-beta1 -DgenerateBackupPoms=false
git checkout master
read -p "Enter your commit message: " commitMessage
read -p "Are you sure: $commitmessage is your message? [Y/N]" response
if [ "$response" == "Y" ];
then
    echo YES
else
    echo NOP
fi
