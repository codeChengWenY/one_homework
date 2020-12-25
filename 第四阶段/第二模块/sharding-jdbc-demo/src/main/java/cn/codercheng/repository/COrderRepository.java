package cn.codercheng.repository;

import cn.codercheng.entity.COrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface COrderRepository extends JpaRepository<COrder,Long> {
}
