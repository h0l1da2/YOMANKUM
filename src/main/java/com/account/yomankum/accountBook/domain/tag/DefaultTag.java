package com.account.yomankum.accountBook.domain.tag;

import static com.account.yomankum.accountBook.domain.tag.DefaultTag.TagColor.BLUE;
import static com.account.yomankum.accountBook.domain.tag.DefaultTag.TagColor.WHITE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum DefaultTag {

    MONTHLY_PAY("월급", WHITE.value, BLUE.value),
    BONUS("보너스", WHITE.value, BLUE.value),
    FOOD("음식", WHITE.value, BLUE.value);

    private String name;
    private String color;
    private String bgc;

    DefaultTag(String name, String color, String bgc){
        this.name = name;
        this.color = color;
        this.bgc = bgc;
    }

    public static List<Tag> getDefaultMainTags(){
        return Arrays.stream(DefaultTag.values()).map(DefaultTag::toEntity).collect(Collectors.toList());
    }

    private static Tag toEntity(DefaultTag tag){
        Color color = new Color(tag.color, tag.bgc);
        return Tag.of(tag.name, color);
    }

    public enum TagColor{

        BLUE("#000000"),
        RED("#000000"),
        WHITE("#000000");

        private String value;

        TagColor(String value){
            this.value = value;
        }

    }

}
