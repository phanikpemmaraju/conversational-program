package org.legacy.it.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTask implements Serializable {

   @NotNull
   @NotEmpty
   private String name;

   @NotNull
   @NotEmpty
   @Valid
   private List<RequestParam> params;

}