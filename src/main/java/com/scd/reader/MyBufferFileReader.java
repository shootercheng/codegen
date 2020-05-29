package com.scd.reader;

import com.scd.util.StrParseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @author James
 */
public class MyBufferFileReader extends BufferedReader {
    private static final String VAR_OPEN = "${";

    private static final String VAR_CLOSE = "}";

    private Properties properties;

    public MyBufferFileReader(Reader in, int sz) {
        super(in, sz);
    }

    public MyBufferFileReader(Reader in) {
        super(in);
    }

    public MyBufferFileReader(Reader in, Properties properties) {
        super(in);
        this.properties = properties;
    }

    @Override
    public String readLine() throws IOException {
        String readLine = super.readLine();
        if (readLine == null) {
            return readLine;
        }
        if (isLineVarMarked(readLine)) {
            readLine = replaceVariable(readLine);
        }
        return readLine;
    }


    private boolean isLineVarMarked(String readLine) {
        return readLine.contains(VAR_OPEN) && readLine.contains(VAR_CLOSE);
    }

    private String replaceVariable(String readLine) {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int leftIndex = readLine.indexOf(VAR_OPEN);
            int rightIndex = readLine.indexOf(VAR_CLOSE);
            if (leftIndex == -1 || rightIndex == -1) {
                stringBuilder.append(readLine);
                break;
            }
            stringBuilder.append(readLine, 0, leftIndex);
            String variable = readLine.substring(leftIndex + VAR_OPEN.length(), rightIndex);
            String value = properties.getProperty(variable);
            if (!StrParseUtil.isEmpty(value)) {
                stringBuilder.append(value);
            }
            readLine = readLine.substring(rightIndex + 1);
        }
        return stringBuilder.toString();
    }
}
