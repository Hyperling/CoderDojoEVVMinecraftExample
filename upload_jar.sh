#!/bin/bash

ssh mc@192.168.1.65 rm -v Programs/minecraft/craftbukkit/plugins/*.jar
 
scp /home/ling/Programs/eclipse-workspace/CoderDojoEVV/target/*.jar mc@192.168.1.65:Programs/minecraft/craftbukkit/plugins
scp_status=$?

if [[ $scp_status == 0 ]]; then
   rm -v /home/ling/Programs/eclipse-workspace/CoderDojoEVV/target/*.jar
fi
