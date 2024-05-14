**Task 2**

2.1  Exception documentation

| HTTP Method | REST Resource           | Possible Exceptions  | Status Code |
|-------------|-------------------------|----------------------|-------------|
| GET         | /carshop/cars           | CarNotFoundException | 404         |
| GET         | /carshop/cars/{id} | CarNotFoundException | 404         |
| POST        | /carshop/cars     | throwing ctx.status  | 500         |
| PUT         | /carshop/cars /{id} | CarNotFoundException | 404         |
| DELETE      | /carshop/cars /{id} | CarNotFoundException | 404         |



ADMIN LOGIN

username: admin1
password: admin123