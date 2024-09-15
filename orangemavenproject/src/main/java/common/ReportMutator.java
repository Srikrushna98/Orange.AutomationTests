package common;

import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;
import utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

@Slf4j
public class ReportMutator extends TestListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(utils.class);

    @Override
    public void onFinish(ITestContext context) {
        Iterator<ITestResult> failedConf = context.getFailedConfigurations().getAllResults().iterator();
        while (failedConf.hasNext()) {
            ITestResult failedTestClass = failedConf.next();
            ITestNGMethod method = failedTestClass.getMethod();
            if (!context.getFailedConfigurations().getResults(method).isEmpty() && method.getMethodName().equalsIgnoreCase("clearData")) {
                log.info("Removing failed config method:" + failedTestClass.getTestClass().toString() + "-> " + method.getMethodName());
                failedConf.remove();
            }
        }
    }
}
