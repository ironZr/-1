package cn.zr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult implements Serializable{
    private Long total;//总记录数
    private List rows;//当前页结果
/*
    @Bulider注解的实现原理：
  public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long total;
        private List rows;

        public Builder total(Long total) {
            this.total = total;
            return this;
        }

        public Builder rows(List rows) {
            this.rows = rows;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    测试：
        PageResult.builder().rows(list).total(page.getTotal()).build()
*/
}
