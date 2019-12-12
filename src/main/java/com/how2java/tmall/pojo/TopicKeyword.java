package com.how2java.tmall.pojo;

public class TopicKeyword {
    String keyword;
    String topic;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "TopicKeyword{" +
                "keyword='" + keyword + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
