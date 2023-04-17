##Mainteny Demo Worker

This is a worker which runs jobs to place payment requests with the Mainteny Demo service and upon processing of the request, either places an order or cancels the order.

It has 3 jobs.
1. Initiates a payment request with the demo service and after the request gets accepted, it immediately checks if it has been processed too and act upon the response.
2. A cron job which periodically polls for successful payment requests in the queue and does the respective processing here.
3. A cron job which periodically polls for failed payment requests in the queue and does the respective processing here.