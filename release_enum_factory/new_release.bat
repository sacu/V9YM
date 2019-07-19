@echo off
java -cp ../target/classes/ org.jiira.CCMain "tools/protobuf.proto" "D:/WorkSpace/java/V9Y/src/main/java/org/jiira/utils/CommandCollection.java" "D:/WorkSpace/unity/CatchFish/Assets/Script/Utils/CommandCollection.cs" "D:/WorkSpace/unity/CatchFish/Assets/LuaScr/org/jiira/utils/CommandCollection.lua" 
cd tools
copy protobuf.proto D:\WorkSpace\unity\xlua_demo\Assets\Script\Graphs\protobuf.txt
release_proto.bat
pause