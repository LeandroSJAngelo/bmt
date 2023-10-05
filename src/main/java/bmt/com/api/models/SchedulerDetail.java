package bmt.com.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table
@Entity(name = "scheduler")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SchedulerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate departureData;
    private String departureLocal;
    private LocalDate arrivalData;
    private String arrivalLocal;
    private String voyage;
    private String vesselVoyage;
    private LocalDate containerGateIn;
    private LocalDate shippingInstructions;
    private LocalDate shippingInstructionsAms;
    private LocalDate verifiedGrossMass;
    private String imoNumber;
    private String callSign;
    private String vesselVoyageCode;

}
