
package com.android.cesova.obd.commands.control;

import com.android.cesova.obd.commands.ObdCommand;
import com.android.cesova.obd.enums.AvailableCommandNames;

import java.io.IOException;
import java.io.InputStream;


public class TroubleCodesObdCommand extends ObdCommand {

    protected final static char[] dtcLetters = {'P', 'C', 'B', 'U'};
    protected final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private StringBuffer codes = null;
    private int howManyTroubleCodes = 0;


    public TroubleCodesObdCommand() {
        super("03");

        codes = new StringBuffer();
    }


    public TroubleCodesObdCommand(int howManyTroubleCodes) {
        super("03");

        codes = new StringBuffer();
        this.howManyTroubleCodes = howManyTroubleCodes;
    }

    public TroubleCodesObdCommand(TroubleCodesObdCommand other) {
        super(other);
        codes = new StringBuffer();
    }

    @Override
    protected void fillBuffer() {
    }

    @Override
    protected void performCalculations() {

        String workingData = getResult().replaceAll("[\r\n]", "");

        int begin = 0; // start at 2nd byte

        for (int i = 0; begin < workingData.length(); i++) {
            begin += 2;

            for (int j = 0; j < 3; j++) {
                String dtc = "";

                byte b1 = hexStringToByteArray(workingData.charAt(begin));

                int ch1 = ((b1 & 0xC0) >> 6);
                int ch2 = ((b1 & 0x30) >> 4);

                dtc += dtcLetters[ch1];
                dtc += hexArray[ch2];

                begin++;

                dtc += workingData.substring(begin, begin + 3);

                if (dtc.equals("P0000")) {
                    return;
                }

                codes.append(dtc);
                codes.append('\n');
                begin += 3;
            }
        }
    }

    private byte hexStringToByteArray(char s) {
        return (byte) ((Character.digit(s, 16) << 4));
    }

    public String formatResult() {
        return codes.toString();
    }

    @Override
    protected void readRawData(InputStream in) throws IOException {
        byte b = 0;
        StringBuilder res = new StringBuilder();

        // read until '>' arrives
        while ((char) (b = (byte) in.read()) != '>') {
            if ((char) b != ' ') {
                res.append((char) b);
            }
        }

        rawData = res.toString().trim();

    }

    @Override
    public String getFormattedResult() {
        return codes.toString();
    }

    @Override
    public String getName() {
        return AvailableCommandNames.TROUBLE_CODES.getValue();
    }

}
