package br.com.brenoborges.finflow.modules.transaction.dtos;

import br.com.brenoborges.finflow.modules.transaction.entities.Category;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryTransactionsDTO {

    private List<Category> categories;

}
