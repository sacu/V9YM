@echo off
svn ci "E:/Bigworld/serv/WakeServer/config/local/saproto.u" -m "数据表提交"
svn ci "E:/Bigworld/unity/dev_0.4/Assets/_Resources/Data/en_saproto.u" -m "数据表提交"
svn ci "E:/Bigworld/serv/WakeServer/src/main/java/com/game/sa/protobuf/SAProtoDecode.java" -m "数据表提交"
svn ci "E:/Bigworld/unity/dev_0.4/Assets/Script/Proto/SAProtoDecode.cs" -m "数据表提交"
svn ci "E:/Bigworld/unity/dev_0.4/Assets/LuaScr/com/game/sa/protobuf/SAProtoDecode.lua" -m "数据表提交"
svn ci "E:/Bigworld/serv/WakeMaster/table/*" -m "数据表提交"
pause