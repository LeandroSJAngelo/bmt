package bmt.com.api.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "voyage_details")
@Entity(name = "voyage_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VoyageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String departureData;
    private String departureLocal;
    private String arrivalData;
    private String arrivalLocal;
    private String voyage;
    private String vesselVoyage;
    private String containerGateIn;
    private String shippingInstructions;
    private String shippingInstructionsAms;
    private String verifiedGrossMass;
    private String imoNumber;
    private String callSign;
    private String vesselVoyageCode;

}
