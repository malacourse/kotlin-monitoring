package com.example.blog
import java.time.Duration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.config.MeterFilter
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig

@Configuration
class FilterSample
{
  @Bean
  fun metricsCommonTags():MeterRegistryCustomizer<MeterRegistry> {


      val hostName = if (System.getenv("HOSTNAME").isNullOrEmpty()) "host" else System.getenv("HOSTNAME")
      System.out.println("++++++++++++++++++++++++++++++++ Mikes Override -" + hostName)

      return MeterRegistryCustomizer<MeterRegistry>
    {
         registry-> registry.config()
            .commonTags("host", hostName)
            .meterFilter(MeterFilter.deny({
              id->
              val uri = id.getTag("uri")
              uri != null && uri.startsWith("/actuator") }))
     }
  }
  companion object {
    private val HISTOGRAM_EXPIRY = Duration.ofMinutes(10)
    private val STEP = Duration.ofSeconds(5)
  }
}