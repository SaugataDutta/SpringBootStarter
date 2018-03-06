package com.tgt.mkt.cam.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by z083387 on 8/3/17.
 */

@Getter
@Setter
@ToString
@Builder
public class KafkaRequestObject {
    @JsonProperty("asset_id")
    @NotNull
    private String assetId;

    @JsonProperty("asset_name")
    @NotEmpty
    private String assetName;

    @JsonProperty("comment")
    private String comment;

}
