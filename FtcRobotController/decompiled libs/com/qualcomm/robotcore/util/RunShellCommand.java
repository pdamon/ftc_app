/*
 * Decompiled with CFR 0_101.
 */
package com.qualcomm.robotcore.util;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.util.RobotLog;
import java.io.IOException;
import java.io.InputStream;

public class RunShellCommand {
    boolean a = false;

    public void enableLogging(boolean enable) {
        this.a = enable;
    }

    public String run(String cmd) {
        if (this.a) {
            RobotLog.v("running command: " + cmd);
        }
        String string = this.a(cmd, false);
        if (this.a) {
            RobotLog.v("         output: " + string);
        }
        return string;
    }

    public String runAsRoot(String cmd) {
        if (this.a) {
            RobotLog.v("running command: " + cmd);
        }
        String string = this.a(cmd, true);
        if (this.a) {
            RobotLog.v("         output: " + string);
        }
        return string;
    }

    private String a(String string, boolean bl) {
        byte[] arrby = new byte[524288];
        int n = 0;
        String string2 = "";
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        Process process = null;
        try {
            if (bl) {
                processBuilder.command("su", "-c", string).redirectErrorStream(true);
            } else {
                processBuilder.command("sh", "-c", string).redirectErrorStream(true);
            }
            process = processBuilder.start();
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            n = inputStream.read(arrby);
            if (n > 0) {
                string2 = new String(arrby, 0, n);
            }
        }
        catch (IOException var8_9) {
            RobotLog.logStacktrace(var8_9);
        }
        catch (InterruptedException var8_10) {
            var8_10.printStackTrace();
        }
        finally {
            if (process != null) {
                process.destroy();
            }
        }
        return string2;
    }

    public static void killSpawnedProcess(String processName, String packageName, RunShellCommand shell) throws RobotCoreException {
        try {
            int n = RunShellCommand.getSpawnedProcessPid(processName, packageName, shell);
            while (n != -1) {
                RobotLog.v("Killing PID " + n);
                shell.run(String.format("kill %d", n));
                n = RunShellCommand.getSpawnedProcessPid(processName, packageName, shell);
            }
        }
        catch (Exception var3_4) {
            throw new RobotCoreException(String.format("Failed to kill %s instances started by this app", processName));
        }
    }

    public static int getSpawnedProcessPid(String processName, String packageName, RunShellCommand shell) {
        String[] arrstring;
        String string = shell.run("ps");
        String string2 = "invalid";
        for (String string32 : string.split("\n")) {
            if (!string32.contains((CharSequence)packageName)) continue;
            arrstring = string32.split("\\s+");
            string2 = arrstring[0];
            break;
        }
        for (String string32 : string.split("\n")) {
            if (!string32.contains((CharSequence)processName) || !string32.contains((CharSequence)string2)) continue;
            arrstring = string32.split("\\s+");
            return Integer.parseInt(arrstring[1]);
        }
        return -1;
    }
}

