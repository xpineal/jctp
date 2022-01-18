module jctp {
    requires org.scijava.nativelib;
    exports org.kr.jctp;

    opens natives.windows_64;
    opens natives.linux_64;
}