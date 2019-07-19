package org.jiira.proto;

import java.util.ArrayList;

public interface ISAProtoDecode {
	void start();
	void process(SAProtoTask task, ArrayList<String> type, ArrayList<String> title);
	void finish(String path);
	String getFragment(String type);
	String getType(String type);
}
