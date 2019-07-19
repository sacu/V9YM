echo on

protoc --version

echo C# ProtoBuf 发布
protoc --descriptor_set_out=protobuf.protobin --include_imports protobuf.proto
protogen protobuf.protobin
copy protobuf.cs D:\WorkSpace\unity\CatchFish\Assets\Script\Proto\ProtobufType.cs

echo Java ProtoBuf 发布
protoc --java_out=D:\WorkSpace\java\V9Y\src\main\java\ protobuf.proto
protoc --java_out=D:\WorkSpace\java\V9YM\src\ protobuf.proto

echo Lua ProtoBuf
protoc --plugin=protoc-gen-lua="protoc-gen-lua.bat" --lua_out=. protobuf.proto
copy protobuf_pb.lua D:\WorkSpace\unity\CatchFish\Assets\LuaScr\org\jiira\protobuf\protobuf_pb.lua

java -cp ../../target/classes/ org.jiira.LuaProtoBuffOptimize D:\WorkSpace\unity\CatchFish\Assets\LuaScr\org\jiira\protobuf\protobuf_pb.lua

PAUSE