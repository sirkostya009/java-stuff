package ua.sirkostya009.javastuff.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "mail")
public class Mail {
    @Id
    private String id;
    @Field(type = FieldType.Auto)
    private List<String> recipients;
    @Field(type = FieldType.Keyword)
    private String subject;
    @Field(type = FieldType.Text)
    private String content;
    @Field(type = FieldType.Auto)
    private MailStatus status;
    @Field(type = FieldType.Text)
    private String errorMessage;
}
