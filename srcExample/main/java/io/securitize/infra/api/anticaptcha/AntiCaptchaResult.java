package io.securitize.infra.api.anticaptcha;

import jakarta.persistence.*;


@Entity
@Table(name = "antiCaptchaResult")
@SuppressWarnings("unused")
public class AntiCaptchaResult {
    private long id;
    private int attemptNumber;
    private long createTime;
    private long endTime;
    private long duration;
    private double cost;
    private boolean isSuccess;

    public AntiCaptchaResult(int attemptNumber, long createTime, long endTime, String cost, boolean isSuccess) {
        this.attemptNumber = attemptNumber;
        this.createTime = createTime;
        this.endTime = endTime;
        this.duration = endTime - createTime;
        this.cost = Double.parseDouble(cost);
        this.isSuccess = isSuccess;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}