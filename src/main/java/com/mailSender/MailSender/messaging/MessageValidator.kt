package com.mailSender.MailSender.messaging

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchema
import ifmo.dma.microdb.utils.JsonSchemaReaderFromResources;
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Component

@Slf4j
@Component
class MessageValidator{
     private   val logger = getLogger(MessageValidator::class.java)

    private val jsonSchemaReaderFromResources =  JsonSchemaReaderFromResources()
    private val mapper = ObjectMapper()
      private val schemasForTopic: Map<String, JsonSchema> = mapOf(
              Pair("deleteFavoriteRecipe", jsonSchemaReaderFromResources
                              .readJsonSchemaFromResource("delete-favorite-recipe")),
              Pair("setFavoriteRecipe",jsonSchemaReaderFromResources
                      .readJsonSchemaFromResource("set-favorite-recipe")),
              Pair("setApproveRecipe",jsonSchemaReaderFromResources
                      .readJsonSchemaFromResource("set-approve-recipe"))
      )
    fun validateViaSchema(topic:String,content: String): Boolean {
        if (schemasForTopic.contains(topic)){

            try {
                val errors = schemasForTopic[topic]!!.validate(mapper.readTree(content))

                if (errors.isNotEmpty()) {
                    errors.forEach { error ->
                        run {
                            logger.warn("Wrong schemas: {$error}")
                        }
                    }
                    return false
                }
            } catch (e: JsonParseException) {
                logger.warn(e.originalMessage)
                return false
            }
        }
        return true
    }
}