package io.okraskat.throttling;

class EndpointMethod {

    private final Class targetClass;
    private final String targetMethod;

    public EndpointMethod(Class targetClass, String targetMethod) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Class getTargetClass() {
        return targetClass;
    }
}
