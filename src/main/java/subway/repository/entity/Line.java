package subway.repository.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import subway.common.Comment;
import subway.controller.request.LineRequest;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("노선 이름")
    @Column(length = 20)
    private String name;

    @Comment("노선 색상")
    @Column(length = 20)
    private String color;

    @Embedded
    private final Sections sections = new Sections();

    @Builder
    private Line(String name, String color, Section section) {
        this.name = name;
        this.color = color;
        sections.addSection(section);
        section.addLine(this);
    }

    public void update(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public void addSection(Section section) {
        section.addLine(this);
        sections.addSection(section);
    }

    public List<Long> getStationIds() {
        return sections.getStationIds();
    }
}
