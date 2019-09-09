# KSQL Testbed

This project is just a kafka SQL playground.

## Setup environment

```
Docker-compose up -d
```

Note: Docker memory is allocated minimally at 8 GB. When using Docker Desktop for Mac, the default Docker memory allocation is 2 GB. You can change the default allocation to 8 GB in Docker > Preferences > Advanced.

## Examples
### Create Data Stream

* Login into ksqk-cli
```
docker-compose exec ksql-cli ksql http://ksql-server:8088
```

* Create Stream
```
  Create order Stream:
	CREATE STREAM orders_raw (
    itemid VARCHAR,
    price DOUBLE,
    location STRUCT<
        city VARCHAR,
        state VARCHAR,
        zipcode INT>,
    timestamp VARCHAR)
 WITH (
    KAFKA_TOPIC='orders_topic',
    VALUE_FORMAT='JSON');
 ```

* Check that is created
```
DESCRIBE orders_raw;
```

### Create Table

* Login into ksqk-cli
```
docker-compose exec ksql-cli ksql http://ksql-server:8088
```

* Create table
```
	Create users Table

      CREATE TABLE users_original (
     registertime BIGINT,
     gender VARCHAR,
     regionid VARCHAR,
     userid VARCHAR)
     WITH (
    kafka_topic='users_topic',
    value_format='JSON',
    key = 'userid');
```

* Check that is created
```
describe users_original;
```

### Generate Random data

```
 docker exec ksql-datagen ksql-datagen bootstrap-server=broker:29092 \
                          quickstart=users \
                          format=json \
                          topic=users_topic \
                          maxInterval=10 \
                          iterations=1000

 docker exec ksql-datagen ksql-datagen bootstrap-server=broker:29092 \
                           quickstart=orders \
                           format=json \
                           topic=orders_topic \
                           maxInterval=10 \
                           iterations=1000

```

### Make a SELECT statement over a Stream or a Table

You can go to Kafka control center (http://localhost:9021) cluster/KSQL/KSQL editor and have a look orderStream and user table in realtime.

```
select * from USERS_ORIGINAL;

select * from ORDERS_RAW;

select *
from ORDERS_RAW
where price IS NOT null;

select * from IMPRESSIONS where USERID = 'user_71';
```

*More example queries*: https://docs.confluent.io/current/ksql/docs/tutorials/examples.html

### CREATE test data from AVRO schema

```
docker exec ksql-datagen ksql-datagen bootstrap-server=broker:29092 \
                          schema=/tmp/person.avcs \
                          format=delimeted \
                          topic=users_topic \
                          maxInterval=10 \
                          iterations=100 key=id
```

Then you can create a stream for this topic (through control center)

*Query*: CREATE STREAM person (viewtime BIGINT, key VARCHAR, userid VARCHAR, adid VARCHAR) WITH (KAFKA_TOPIC='impressions', VALUE_FORMAT='DELIMITED');




