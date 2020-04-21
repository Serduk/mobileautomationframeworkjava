package tests;

import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.LoggerBase;
import utils.TestConstants;

public class CustomRetryAnalyzer extends LoggerBase implements IRetryAnalyzer {
  private int MAX_RETRIES = TestConstants.Constants.getMaxRetries();
  private AtomicInteger count = new AtomicInteger(MAX_RETRIES);

  private boolean isRetryAvailable() {
    return (count.intValue() > 0);
  }

  @Override
  public boolean retry(ITestResult result) {
    boolean retry = false;
    log.info(String.format("MAX_RETRIES: %s", MAX_RETRIES));
    if (isRetryAvailable()) {
      log.info(String.format("Retrying test case: %s, %s out of %s", result.getMethod(), (MAX_RETRIES - count.intValue() + 1), MAX_RETRIES));
      retry = true;
      count.decrementAndGet();
    }
    return retry;
  }
}