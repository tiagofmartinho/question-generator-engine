package pt.iscte.questionengine.entity

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditableEntity {

    @Column(name = "created_date") @CreatedDate private var createdDate: LocalDateTime? = null
}
