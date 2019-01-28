package com.virtualskillset.discountmania.helper;

public class User  {
        private int id;
        private String number,role,status,name ;

        public User(int id,String number, String role,String status,String name) {
            this.id = id;
            this.number = number;
            this.role = role;
            this.status=status;
            this.name=name;
        }


//        public int getId() {
//            return id;
//        }

        public int getId() {
        return id;
    }

        public String getStatus() { return status; }

        public String getNumber() {
            return number;
        }


        public String getRole() {
            return role;
        }

        public String getName() {
        return name;
    }
}
