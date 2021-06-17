package com.theenm.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class LogManager
{
    private static final String TAG = LogManager.class.getName();
    public static String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SMS_LOG";

    public static String mFileName = "";
    private static FileWriter mFileWriter = null;
    private static boolean mUseLog = false;
    private static final String strNewLine = System.getProperty("line.separator");

//    public static void setPath(String strPath, String strFileName)
//    {
//    	mPath = strPath;
//    	mFileName = mPath + "/" + strFileName + ".log";
//    }
//    
//    public static void setUseLog(boolean bUse)
//    {
//    	mUseLog = bUse;
//    }
    
    public static void writeLog(String msg)
    {
//    	if( !mUseLog )
//    		return;
    	
    	if( mPath == null || mPath.length() < 1 )
    	    return;
    	
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
        
//        if( mFileName == null || mFileName.length() < 1 )
//        {
//            String strDate = formatter1.format(new Date());
//            mFileName = mPath + "/" + strDate + ".log";
//        }
        
        String strDate = formatter1.format(new Date());
        mFileName = mPath + "/" + strDate + ".txt";

        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        String stTime = formatter2.format(new Date());

        String appendMsg = "";
        StringBuffer bufLogMsg = new StringBuffer();
        StackTraceElement[] trace = new Throwable().getStackTrace();

        if( trace.length >= 1 )
        {
            StackTraceElement elt = trace[1];
            appendMsg = elt.getFileName() + ": " + elt.getClassName() + "."
                    + elt.getMethodName() + "(line " + elt.getLineNumber()
                    + "): ";
        }

        bufLogMsg.append("[");
        bufLogMsg.append(stTime);
        bufLogMsg.append("] ");
        bufLogMsg.append(appendMsg);
        bufLogMsg.append(msg);

        File fLogPath = new File(mPath);
        if( !fLogPath.exists() )
            fLogPath.mkdirs();

        try
        {

            mFileWriter = new FileWriter(mFileName, true);
            mFileWriter.write(bufLogMsg.toString());
            mFileWriter.write(strNewLine);
        }
        catch( IOException e )
        {
        	Log.e(TAG, ""+e.getMessage());
        }
        finally
        {
            try
            {
                mFileWriter.close();
            }
            catch( Exception e1 )
            {
            }
        }
    }

    // LogManager.writeLog("rstCode:" + strRstCode + ", rstMsg:" + strMsg);

}
