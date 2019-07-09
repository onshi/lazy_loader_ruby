package com.centzy.lazyloader;

import java.util.concurrent.Callable;

/**
 * @author Peter Edge (peter.edge@gmail.com).
 */
public class LazyLoaderDelegate {

  private final Callable<Object> callable;

  private volatile LazyLoaderResult lazyLoaderResult;

  public LazyLoaderDelegate(Callable<Object> callable) {
    this.callable = callable;
  }

  public Object get() throws LazyLoaderException {
    LazyLoaderResult result = lazyLoaderResult;
    if (result == null) {
      synchronized (this) {
        result = lazyLoaderResult;
        if (result == null) {
          try {
            lazyLoaderResult = result = new LazyLoaderResult(callable.call(), null);
          } catch (Exception e) {
            lazyLoaderResult = result = new LazyLoaderResult(null, new LazyLoaderException(e));
          }
        }
      }
    }
    return result.get();
  }

  private static class LazyLoaderResult {

    private final Object object;
    private final LazyLoaderException lazyLoaderException;

    LazyLoaderResult(Object object, LazyLoaderException lazyLoaderException) {
      this.object = object;
      this.lazyLoaderException = lazyLoaderException;
    }

    Object get() throws LazyLoaderException {
      if (lazyLoaderException != null) {
        throw lazyLoaderException;
      }
      return object;
    }
  }
}
