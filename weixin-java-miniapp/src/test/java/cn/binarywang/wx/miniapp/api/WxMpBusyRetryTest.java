package cn.binarywang.wx.miniapp.api;

import cn.binarywang.wx.miniapp.api.impl.WxMpServiceApacheHttpClientImpl;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Test
public class WxMpBusyRetryTest {

  @DataProvider(name = "getService")
  public Object[][] getService() {
    WxMpService service = new WxMpServiceApacheHttpClientImpl() {

      @Override
      public synchronized <T, E> T executeInternal(
        RequestExecutor<T, E> executor, String uri, E data)
        throws WxErrorException {
        this.log.info("Executed");
        WxError error = new WxError();
        error.setErrorCode(-1);
        throw new WxErrorException(error);
      }
    };

    service.setMaxRetryTimes(3);
    service.setRetrySleepMillis(500);
    return new Object[][]{{service}};
  }

  @Test(dataProvider = "getService", expectedExceptions = RuntimeException.class)
  public void testRetry(WxMpService service) throws WxErrorException {
    service.execute(null, null, null);
  }

  @Test(dataProvider = "getService")
  public void testRetryInThreadPool(final WxMpService service) throws InterruptedException, ExecutionException {
    // 当线程池中的线程复用的时候，还是能保证相同的重试次数
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println("=====================");
          System.out.println(Thread.currentThread().getName() + ": testRetry");
          service.execute(null, null, null);
        } catch (WxErrorException e) {
          throw new RuntimeException(e);
        } catch (RuntimeException e) {
          // OK
        }
      }
    };
    Future<?> submit1 = executorService.submit(runnable);
    Future<?> submit2 = executorService.submit(runnable);

    submit1.get();
    submit2.get();
  }

}
