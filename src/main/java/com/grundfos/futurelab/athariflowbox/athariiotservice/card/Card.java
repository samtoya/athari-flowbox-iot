package com.grundfos.futurelab.athariflowbox.athariiotservice.card;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.Auditable;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "cards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Card extends Auditable {
    @Id
    private String id;
    @Column(unique = true)
    private String serialNumber;
    private String holder;
    private LocalDateTime disabledAt;
    @Column(columnDefinition = "text")
    private String reason;

    public static Card fromDto(CardDto dto) {
        Card card = new Card();
        BeanUtils.copyProperties(dto, card);
        card.setId(UUID.randomUUID().toString());
        return card;
    }

    public Boolean getIsEnabled() {
        return disabledAt == null && reason == null;
    }
}
