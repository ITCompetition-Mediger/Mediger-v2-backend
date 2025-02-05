package net.mediger.member.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "concerns", column = @Column(name = "health_concerns")),
        @AttributeOverride(name = "focus", column = @Column(name = "health_focus")),
        @AttributeOverride(name = "chronicDisease", column = @Column(name = "health_chronic_disease")),
        @AttributeOverride(name = "interestedDisease", column = @Column(name = "health_interested_disease"))
})
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class HealthInfo {

    private String concerns;

    private String focus;

    private String chronicDisease;

    private String interestedDisease;
}
