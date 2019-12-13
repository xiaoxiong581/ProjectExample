#!/bin/bash

rm -f midSend.log
rm -f countSend.log
cat result.log | grep send | while read line
do
  key=${line: 52: 36}
  echo $key >> midSend.log
done

cat midSend.log | sort | uniq -c | sort -n >> countSend.log
sendNum=`cat midSend.log | wc -l`
sendSingleNum=`grep -c '1 ' countSend.log`
echo "send num: $sendNum, singleNum:$sendSingleNum"

rm -f midRec.log
rm -f countRec.log
cat result.log | grep receive | while read line
do
  key=${line: 55: 36}
  echo $key >> midRec.log
done

cat midRec.log | sort | uniq -c | sort -n >> countRec.log
recNum=`cat midRec.log | wc -l`
recSingleNum=`grep -c '1 ' countRec.log`
echo "receive num: $recNum, singleNum:$recSingleNum"

if [ $sendSingleNum != $recSingleNum ]; then
  echo "recevie messages duplicate"
fi
