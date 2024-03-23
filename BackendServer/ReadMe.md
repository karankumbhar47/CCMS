## Backend Server

1. **Install Maven** 
    - For Ubuntu 
    ```bash
    sudo apt install maven 
    ```
    
2. **To generate Openapi code** 
    - navigate to BackendServer directory and execute 
     following command
    - Don't user `Clean` and `Complile` Approach
     for generating code 
    ```bash
   mvn generate-sources
   ``` 
   
3. Please update .env File(Template file is .env.exaple) 

4. Write API definition in main.yaml file, linked to directory 
   `src/main/resources/api/` directory 