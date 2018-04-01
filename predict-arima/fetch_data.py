import time
import pandas as pd
import csv
from cassandra.cluster import Cluster
from cassandra.protocol import NumpyProtocolHandler
from cassandra.auth import PlainTextAuthProvider
from cassandra.query import tuple_factory

clusterhosts=['54.191.120.32']

auth = PlainTextAuthProvider(username='cassandra',
                             password='cassandra')


def pandas_factory(colnames, rows):
    return pd.DataFrame(rows, columns=colnames)


cluster = Cluster(clusterhosts, auth_provider=auth)
s = cluster.connect('demo')
s.row_factory = tuple_factory
#.client_protocol_handler = NumpyProtocolHandler
#s.row_factory = pandas_factory
#s.default_fetch_size = None
result = s.execute("SELECT last_update, available_bikes FROM demo.dublin_bikes_json2")
#df = result._current_rows

c = 0
resultant = []
res = tuple()
for date, bikes in result:
    c = c + 1
    #print(round(bikes, 2))
    res = (date, round(bikes, 2))
    resultant.append(res)
print(c)
print (resultant)

with open('predict_data.csv','wb') as out:
    csv_out=csv.writer(out)
    csv_out.writerow(['last_update','available_bikes'])
    for t in resultant:
        #print t
        csv_out.writerow(t)



print ("shutting down")
start = time.time()
cluster.shutdown()
eta = time.time() - start
print ("shutdown took %s seconds" % eta)
