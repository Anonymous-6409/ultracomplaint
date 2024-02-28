package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.Tbl_Complaints;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.enums.ComplainStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Chat_Repo extends JpaRepository<Tbl_Complaints, String> {

    List<Tbl_Complaints> findByAssignedToEngineerAndStatus(Tbl_Engineer_Details eng, ComplainStatus status);

    List<Tbl_Complaints> findByCreatedByCUstomer(Tbl_Customer_details eng);

    Tbl_Complaints findByComplainIdAndCreatedByCUstomer(String complaiId, Tbl_Customer_details cust);

    Tbl_Complaints findByComplainIdAndAssignedToEngineer(String complainId, Tbl_Engineer_Details eng);

    List<Tbl_Complaints> findByStatus(ComplainStatus status);

    List<Tbl_Complaints> findByStatusAndCreatedByCUstomer(ComplainStatus status, Tbl_Customer_details customerDetails);

    Tbl_Complaints findByComplainId(String id);

    long countByStatus(ComplainStatus status);

    List<Tbl_Complaints> findByStatusOrderByCreatedDateDesc(ComplainStatus status);

}
