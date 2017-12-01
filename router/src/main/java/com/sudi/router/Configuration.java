package com.sudi.router;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public class Configuration {
    boolean debuggable;
    String[] modules;

    private Configuration() {
    }

    public static class Builder {
        private boolean debuggable;
        private String[] modules;

        public Builder setDebuggable(boolean debuggable) {
            this.debuggable = debuggable;
            return this;
        }

        public Builder registerModules(String... modules) {
            this.modules = modules;
            return this;
        }

        public Configuration build() {
            if (modules == null || modules.length == 0) {
                throw new RuntimeException("You must call registerModules() to initialize ActivityRouter.");
            }
            Configuration configuration = new Configuration();
            configuration.debuggable = this.debuggable;
            configuration.modules = this.modules;
            return configuration;
        }
    }
}
