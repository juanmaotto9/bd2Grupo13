mysql -u root -e "drop database if exists bd2_grupo13;
                  create database bd2_grupo13;
                  CREATE USER IF NOT EXISTS bd2_g13 IDENTIFIED BY 'grupo13';
                  GRANT ALL PRIVILEGES ON bd2_grupo13.* TO 'bd2_g13' with grant option;
                  FLUSH PRIVILEGES;
                  "