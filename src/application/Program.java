package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		

		System.out.println("==TEST 1: department findById==");
		Department dep = departmentDao.findById(3);
		System.out.println(dep);	
		
		System.out.println("==TEST 2: department findAll==");
		List<Department>list = departmentDao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("==TEST 3: department insert==");
		Department newDep = new Department(null, "Books");
		departmentDao.insert(newDep);
		System.out.println("Inserted! New id = " + newDep.getId());
		
		System.out.println("==TEST 4: department update==");
		dep = departmentDao.findById(6);
		dep.setName("Music");
		departmentDao.update(dep);
		System.out.println("Update Completed");
		
		System.out.println("==TEST 5: department delete==");
		System.out.println("Enter id for delete test :");
		departmentDao.deleteById(sc.nextInt());
		System.out.println("Delete completed");
		
		System.out.println("==TEST 1: seller findById==");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);	
		
		System.out.println();
		
		System.out.println("==TEST 2: seller findByDepartment==");
		Department department = new Department (2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(department);
		for(Seller obj : listSeller) {
			System.out.println(obj);
		}
		
		System.out.println("==TEST 3: seller findAll==");
		listSeller = sellerDao.findAll();
		for(Seller obj : listSeller) {
			System.out.println(obj);
		}
		
		System.out.println("==TEST 4: seller insert==");
		Seller newSeller = new Seller(null, "Tommy Vercetti", "tommy@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("==TEST 5: seller update==");
		seller = sellerDao.findById(10);
		seller.setName("John Marston");
		seller.setEmail("outlaw@gmail.com");
		sellerDao.update(seller);
		System.out.println("Update Completed");
		
		System.out.println("==TEST 5: seller delete==");
		System.out.println("Enter id for delete test :");
		sellerDao.deleteById(sc.nextInt());
		System.out.println("Delete completed");
		
		sc.close();
		
	}
}

