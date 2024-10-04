curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiMzY0Yzk2NjU4OTgzM2QzNTE0YTBhYjAyZGFkYjdmMjkiLCJleHAiOjE3MjgwMjM2NzE2MzQsInRpbWVzdGFtcCI6MTcyODAyMTg3MTYzOX0.1Tussy8yfHBNDKypBiv2P8fnN01MVR5IuJtwgL_puao" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions
