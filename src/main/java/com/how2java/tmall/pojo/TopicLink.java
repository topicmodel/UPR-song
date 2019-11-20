package com.how2java.tmall.pojo;

public class TopicLink {
    String source;
    String target;
    Double value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TopicLink{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", value=" + value +
                '}';
    }
}
