package testSpringBoot.tests.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath, mediaType;
    private long fileSize;

    @Lob
    private byte[] data;

    @OneToOne
    private Student student;

    public Avatar(Long id, String filePath, String mediaType, long fileSize, Student student) {
        this.id = id;
        this.filePath = filePath;
        this.mediaType = mediaType;
        this.fileSize = fileSize;
        this.student = student;
    }
}
